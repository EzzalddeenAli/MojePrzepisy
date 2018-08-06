#!flask/bin/python

from functools import wraps
from flask import Flask, jsonify, request, session

import os

# My libraries
from Constans import *
from Database import *
from Users import *
from recipe_elements.Cards import *
from recipe_elements.Recipes import *
from Session import *

# Global definitions
app = Flask(__name__)
database = None


# -----------------------------
# Utilities

def authorized(f):
    '''
        This is a helpful decorator to be used when user's authentication
    should be enforced upon entering endpoint's route. It does it's job by
    creating Session's object and validating that session.

    returns:
        If session is valid - the decorated function becomes called and it's
        value is returned.

        Otherwise, the Unauthorized JSON is returned:
            {
                "status": 401,
                "message": "Unauthorized. Please login first."
            }
    '''

    @wraps(f)
    def validator(*args, **kwargs):
        sess = Session(session, database)
        if not sess.validate_session():
            Logger.fail('@authorized: User not authenticated.')
            return jsonify({
                'status': 401,
                'message': 'Unauthorized. Please login first.'
            })
        else:
            Logger.ok('@authorized: User authenticated.')
            return f(*args, **kwargs)

    return validator


def get_user_id():
    sess = Session(session, database)
    return sess.get_user_id()


# -----------------------------
# Server code

# Login session
@app.route('/login', methods=['POST'])
def login_method():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')

    status, message, userID = user.loginUser(login, password)

    if status == 200:
        sess = Session(session, database)
        sess.create_session(userID)

        Logger.dbg('login(): userID = {}'.format(str(userID)))
        Logger.ok('User "{}" has been logged.'.format(login))
    else:
        Logger.fail('Could not authenticate user: "{}"'.format(login))

    return jsonify({
        'status': status,
        'message': message
    })


# Logout session
@app.route('/logout', methods=['GET'])
@authorized
def logout_method():
    sess = Session(session, database)
    sess.destroySession()

    return jsonify({
        'status': 200,
        'message': 'You have been logged out.'
    })


@app.route('/user', methods=['PUT'])
def registration():
    user = Users(database)
    params = request.get_json()

    login = params.get('login')
    password = params.get('password')
    firstName = params.get('firstName')
    lastName = params.get('lastName')
    email = params.get('email')

    status, message = user.registerUser(login, password, firstName, lastName, email)

    return jsonify({
        'status': status,
        'message': message
    })


# Operations performed on the profile
@app.route('/user', methods=['GET', 'DELETE'])
@authorized
def profile_method():
    user = Users(database)

    # Show user data
    if request.method == 'GET':
        return jsonify(user.getUser(get_user_id()))

    # Delete user
    elif request.method == 'DELETE':
        status, message = user.deleteUser(get_user_id())

        return jsonify({
            'status': status,
            'message': message
        })


@app.route('/user/<string:columnName>/<string:columnValue>', methods=['POST'])
@authorized
def editUser(columnName, columnValue):
    user = Users(database)

    status, message = user.editUser(columnName, columnValue, get_user_id())

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/cards/searchedCards', methods=['POST'])
def getSearchedCards():
    card = Cards(database)
    params = request.get_json()
    searchedQuery = params.get('recipeName')

    cards = card.getSearchedCardsSortedByDefault(searchedQuery)

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/cards/<string:sorted_method>', methods=['GET'])
def getSortedCards(sorted_method):
    card = Cards(database)
    if sorted_method == 'default':
        cards = card.getAllCards()
    elif sorted_method == 'alphabetically':
        cards = card.getAllCardsSortedAlphabetically()
    elif sorted_method == 'lastAdded':
        cards = card.getAllCardsSortedByLastAdded()
    elif sorted_method == 'highestRated':
        cards = card.getAllCardsSortedByHighestRated()

    if not cards:
        Logger.fail("There were no cards returned!")
    return jsonify(cards)


@app.route('/recipe/<int:recipeId>', methods=['GET'])
def getRecipe(recipeId):
    recipe = Recipes(database)

    recipes = recipe.getRecipe(recipeId)

    if not recipes:
        Logger.fail("There was no recipe returned!")
    return jsonify(recipes)


@app.route('/recipe/<int:recipeId>', methods=['DELETE'])
@authorized
def deleteRecipe(recipeId):
    recipe = Recipes(database)

    status, message = recipe.deleteRecipe(recipeId)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe', methods=['PUT'])
@authorized
def addRecipe():
    recipe = Recipes(database)
    user = Users(database)

    userID = user.getUser(get_user_id())

    params = request.get_json()

    recipeName = params.get('recipeName')
    recipeDescription = params.get('recipeDescription')
    recipePrepareTime = params.get('recipePrepareTime')
    recipeCookTime = params.get('recipeCookTime')
    recipeBakeTime = params.get('recipeBakeTime')
    recipeMainPictureId = params.get('recipeMainPictureId')
    recipeCategory = params.get('recipeCategory')


    status, message = recipe.addRecipe(userID, recipeName, recipeDescription,
                                        recipePrepareTime, recipeCookTime, recipeBakeTime,
                                        recipeMainPictureId, recipeCategory)

    return jsonify({
        'status': status,
        'message': message
    })


@app.route('/recipe/<int:recipeId>/<string:columnName>/<string:columnValue>/', methods=['POST'])
@authorized
def recipe_method(recipeId, columnName, columnValue):
    recipe = Recipes(database)

    status, message = recipe.editRecipe(columnName, columnValue, recipeId)

    return jsonify({
        'status': status,
        'message': message
    })


def main():
    global database
    database = Database()
    if not database.connection(host=HOST, user=USER, password=PASSWORD, db=DATABASE):
        Logger.err("Database connection failed")
        return

    # This launches server
    app.secret_key = os.urandom(32)
    app.run(debug=True)


if __name__ == '__main__':
    main()

from Logger import *
from utils import *


class Stars:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getStars(self, recipeID, userId):
        query = u"SELECT favorivte AS favoritesCount, stars AS starsCount " \
                u"FROM users_recipes_stars " \
                u"WHERE recipe_id LIKE '{}' AND user_id LIKE '{}')".format(recipeID, userId)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(queryResult)
            return queryResult
        else:
            return {}

    def addStars(self, userID, recipeId, starsCount, favoritesCount):

        query = u"INSERT INTO users_recipes_stars " \
                u"(user_id, recipe_id, favorite, stars) " \
                u"values ({}, {}, {}, {} )" .format(userID, recipeId, starsCount, favoritesCount)

        queryResult, rows, msg = self.database.insert(query)

        if queryResult:
            Logger.dbg(queryResult)
            Logger.ok("OK. Stars has been added")
            return 200,  u'Forwarded data are correct'
        else:
            Logger.fail("NOT OK. Recipe hasn't been added")
            return 404, u'Forwarded data are not correct'

    def editStars(self, columnName, columnValue, recipeId, userID):
        query = u"UPDATE users_recipes_stars " \
                u"SET {} = '{}'" \
                u"WHERE recipe_id = {}" \
                u"AND user_id = {})".format(columnName, columnValue, recipeId, userID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your changed {}={}'.format(columnName, columnValue)
        else:
            return 404, u'Forwarded data to check are not correct'

    def deleteRecipe(self, recipeId, userID):
        query = u"DELETE FROM users_recipes_stars " \
                u"WHERE recipe_id = {}" \
                u"AND user_id = {})".format(recipeId, userID)

        queryResult = self.database.query(query)

        if queryResult:
            Logger.dbg(str(tuple(queryResult)))
            return 200, u'Your deleted recipe_id = {}'.format(recipeId)
        else:
            return 404, u'Forwarded data to check are not correct'
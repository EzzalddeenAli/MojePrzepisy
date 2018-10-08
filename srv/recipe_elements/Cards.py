from Logger import *
import json
from decimal import Decimal as D
import re


class Cards:
    database = None

    def __init__(self):
        pass

    def __init__(self, database):
        self.database = database

    def setDatabase(self,database):
        self.database = database

    def getAllCards(self, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, "\
        u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date "\
        u"FROM recipes; "

        return self.getAllCardsBasedMethod(userID,recipeQuery)


    def getAllCardsSortedAlphabetically(self, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, " \
                      u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date " \
                      u"FROM recipes " \
                      u"ORDER BY recipeName; "

        return self.getAllCardsBasedMethod(userID,recipeQuery)

    def getAllCardsSortedByLastAdded(self, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, " \
                      u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date " \
                      u"FROM recipes" \
                      u"ORDER BY Date; "

        return self.getAllCardsBasedMethod(userID,recipeQuery)

    def getAllCardsSortedByHighestRated(self, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, "\
        u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date "\
        u"FROM recipes; "

        result = self.getAllCardsBasedMethod(userID,recipeQuery)
        newList = sorted(result, key=lambda k : k['starsCount'], reverse=True)
        Logger.dbg(newList)

        return newList

    def getSearchedCardsSortedByDefault(self, searchedQuery, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, " \
                      u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date " \
                      u"FROM recipes" \
                      u"WHERE R.recipe_name LIKE '%{}%'"\
                      u"GROUP BY R.recipe_id; ".format(searchedQuery)

        return self.getAllCardsBasedMethod(userID,recipeQuery)

    def getAllCardsSortedByUser(self, userID):
        recipeQuery = u"SELECT recipe_id AS recipeId, recipe_name AS recipeName, user_id AS userId, " \
                      u"recipe_main_picture as recipeMainPicture, recipe_created_date_time as Date " \
                      u"FROM recipes" \
                      u"WHERE R.user_id LIKE '{}'" \
                      u"GROUP BY R.recipe_id ".format(userID)
        return self.getAllCardsBasedMethod(userID,recipeQuery)

    def getAllCardsBasedMethod(self, userID, recipeQuery):
        recipeQueryResult = self.database.query(recipeQuery)

        userQueryResult = self.getUserQueryResult()
        starsCountQueryResult = self.getStarsCountQueryResult()
        favoriteCountQueryResult = self.getFavoriteCountQueryResult()
        favoriteQueryResult = self.getFavoriteQueryResult(userID)

        mainQueryResult = list(recipeQueryResult[:])

        if mainQueryResult:
            mainQueryResultWithUser = self.addUserToMainQueryResult(mainQueryResult, userQueryResult)
            mainQueryResultWithUserAndStars = self.addStarsCountToMainQueryResult(mainQueryResultWithUser, starsCountQueryResult)
            mainQueryResultWithUserAndStarsAndFavorite = self.addfavoriteCountToMainQueryResult(mainQueryResultWithUserAndStars, favoriteCountQueryResult)
            mainQueryResultWithUserAndStarsAndFavoriteByUser = self.addFavoriteToMainQueryResult(mainQueryResultWithUserAndStarsAndFavorite, favoriteQueryResult, userID )

            Logger.dbg(mainQueryResultWithUserAndStarsAndFavoriteByUser)
            return mainQueryResultWithUserAndStarsAndFavoriteByUser
        else:
            return [{}]

    def getUserQueryResult(self):
        userQuery = u"SELECT user_id AS userId, user_login AS authorName " \
                    u"FROM users; "
        userQueryResult = self.database.query(userQuery)
        return userQueryResult

    def getStarsCountQueryResult(self):
        starsCountQuery = u"SELECT recipe_id AS recipeId, ROUND(avg(stars), 0) AS starsCount " \
                          u"FROM users_recipes_stars " \
                          u"WHERE stars > 0 " \
                          u"GROUP BY recipeId; "
        starsCountQueryResult = self.database.query(starsCountQuery)
        return starsCountQueryResult

    def getFavoriteCountQueryResult(self):
        favoriteCountQuery = u"SELECT recipe_id AS recipeId, count(favorite) AS favoritesCount " \
                             u"FROM users_recipes_stars " \
                             u"where favorite = true " \
                             u"GROUP BY recipeId; "
        favoriteCountQueryResult = self.database.query(favoriteCountQuery)
        return favoriteCountQueryResult

    def getFavoriteQueryResult(self, userID):
        favoriteQuery = u"SELECT recipe_id AS recipeId, favorite " \
                        u"FROM users_recipes_stars " \
                        u"where user_id = {}; ".format(userID)
        favoriteQueryResult = self.database.query(favoriteQuery)
        return favoriteQueryResult

    def addUserToMainQueryResult(self, mainQueryResult, userQueryResult):
        for mainQueryRow in mainQueryResult:
            for userQueryRow in userQueryResult:
                if mainQueryRow['userId'] == userQueryRow['userId']:
                    mainQueryRow['authorName'] = userQueryRow['authorName']
        return mainQueryResult

    def addStarsCountToMainQueryResult(self, mainQueryResult, starsCountQueryResult):
        for mainQueryRow in mainQueryResult:
            for starsQueryRow in starsCountQueryResult:
                if mainQueryRow['recipeId'] == starsQueryRow['recipeId']:
                    mainQueryRow['starsCount'] = starsQueryRow['starsCount']
        return mainQueryResult

    def addfavoriteCountToMainQueryResult(self, mainQueryResult, favoriteCountQueryResult):
        for mainQueryRow in mainQueryResult:
            for favoriteCountQueryRow in favoriteCountQueryResult:
                if mainQueryRow['recipeId'] == favoriteCountQueryRow['recipeId']:
                    mainQueryRow['favoritesCount'] = favoriteCountQueryRow['favoritesCount']
        return mainQueryResult

    def addFavoriteToMainQueryResult(self, mainQueryResult, favoriteQueryResult, userID):
        if not userID == -1:
            for mainQueryRow in mainQueryResult:
                mainQueryRow['favorite'] = False
                for favoriteQueryRow in favoriteQueryResult:
                    if mainQueryRow['recipeId'] == favoriteQueryRow['recipeId']:
                        mainQueryRow['favorite'] = favoriteQueryRow['favorite']
                        if mainQueryRow['favorite'] is None:
                            mainQueryRow['favorite'] = False
                        if mainQueryRow['favorite'] == 0:
                            mainQueryRow['favorite'] = False
                        if mainQueryRow['favorite'] == 1:
                            mainQueryRow['favorite'] = True
            return mainQueryResult

        else:
            for mainQueryRow in mainQueryResult:
                mainQueryRow['favorite'] = False
            return mainQueryResult
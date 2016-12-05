(function() {
    'use strict';
    angular
        .module('bukreaderApp')
        .factory('Borrows', Borrows);

    Borrows.$inject = ['$resource', 'DateUtils'];

    function Borrows ($resource, DateUtils) {
        var resourceUrl =  'api/borrows/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_from = DateUtils.convertLocalDateFromServer(data.date_from);
                        data.date_to = DateUtils.convertLocalDateFromServer(data.date_to);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_from = DateUtils.convertLocalDateToServer(copy.date_from);
                    copy.date_to = DateUtils.convertLocalDateToServer(copy.date_to);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_from = DateUtils.convertLocalDateToServer(copy.date_from);
                    copy.date_to = DateUtils.convertLocalDateToServer(copy.date_to);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

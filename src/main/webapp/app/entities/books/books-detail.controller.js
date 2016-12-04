(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('BooksDetailController', BooksDetailController);

    BooksDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Books'];

    function BooksDetailController($scope, $rootScope, $stateParams, previousState, entity, Books) {
        var vm = this;

        vm.books = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bukreaderApp:booksUpdate', function(event, result) {
            vm.books = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

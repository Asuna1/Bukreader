(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('BorrowsDetailController', BorrowsDetailController);

    BorrowsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Borrows'];

    function BorrowsDetailController($scope, $rootScope, $stateParams, previousState, entity, Borrows) {
        var vm = this;

        vm.borrows = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bukreaderApp:borrowsUpdate', function(event, result) {
            vm.borrows = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

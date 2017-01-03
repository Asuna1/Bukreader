(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('UserManagementDetailController', UserManagementDetailController);

    UserManagementDetailController.$inject = ['$stateParams', 'Borrows'];

    function UserManagementDetailController ($stateParams, Borrows) {
        var vm = this;

        vm.load = load;
        vm.borrows = {};

        vm.load($stateParams.login);

        function load (login) {
            Borrows.get({login: login}, function(result) {
                vm.borrows = result;
            });
        }
    }
})();

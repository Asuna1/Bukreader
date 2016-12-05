(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('BorrowsDeleteController',BorrowsDeleteController);

    BorrowsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Borrows'];

    function BorrowsDeleteController($uibModalInstance, entity, Borrows) {
        var vm = this;

        vm.borrows = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Borrows.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

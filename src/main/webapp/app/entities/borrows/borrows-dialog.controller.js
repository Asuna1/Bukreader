(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('BorrowsDialogController', BorrowsDialogController);

    BorrowsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Borrows'];

    function BorrowsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Borrows) {
        var vm = this;

        vm.borrows = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.borrows.id !== null) {
                Borrows.update(vm.borrows, onSaveSuccess, onSaveError);
            } else {
                Borrows.save(vm.borrows, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bukreaderApp:borrowsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_from = false;
        vm.datePickerOpenStatus.date_to = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

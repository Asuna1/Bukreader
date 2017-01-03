(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('BorrowsDialogController', BorrowsDialogController);

    BorrowsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Borrows', 'Books'];

    function BorrowsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Borrows, Books) {
        var vm = this;

        vm.books = entity;
        vm.borrows = entity;

        vm.borrows.user_id = "login";
        vm.borrows.book_id = vm.books.isbn;
        vm.borrows.is_activated = false;
        vm.borrows.is_waiting = false;
        vm.borrows.price = 5;

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

(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .controller('TestController', TestController);

    TestController.$inject = ['Test', '$state'];

    function TestController(Test, $state) {
        var vm = this;


        vm.loadAll = loadAll;
        vm.setActive = setActive;



        vm.loadAll();



        function loadAll () {
            Test.query({

            });
        }

    }
})();

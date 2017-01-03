(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('test', {
            parent: 'costam',
            url: '/test',
            data: {
                pageTitle: 'Tests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/costam/test/test.html',
                    controller: 'TestController',
                    controllerAs: 'vm'
                }
            }
            }) }
            })();

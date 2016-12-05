(function() {
    'use strict';

    angular
        .module('bukreaderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('borrows', {
            parent: 'entity',
            url: '/borrows?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Borrows'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/borrows/borrows.html',
                    controller: 'BorrowsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('borrows-detail', {
            parent: 'entity',
            url: '/borrows/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Borrows'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/borrows/borrows-detail.html',
                    controller: 'BorrowsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Borrows', function($stateParams, Borrows) {
                    return Borrows.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'borrows',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('borrows-detail.edit', {
            parent: 'borrows-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/borrows/borrows-dialog.html',
                    controller: 'BorrowsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Borrows', function(Borrows) {
                            return Borrows.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('borrows.new', {
            parent: 'borrows',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/borrows/borrows-dialog.html',
                    controller: 'BorrowsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                book_id: null,
                                user_id: null,
                                date_from: null,
                                date_to: null,
                                is_activated: null,
                                is_waiting: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('borrows', null, { reload: 'borrows' });
                }, function() {
                    $state.go('borrows');
                });
            }]
        })
        .state('borrows.edit', {
            parent: 'borrows',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/borrows/borrows-dialog.html',
                    controller: 'BorrowsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Borrows', function(Borrows) {
                            return Borrows.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('borrows', null, { reload: 'borrows' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('borrows.delete', {
            parent: 'borrows',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/borrows/borrows-delete-dialog.html',
                    controller: 'BorrowsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Borrows', function(Borrows) {
                            return Borrows.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('borrows', null, { reload: 'borrows' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

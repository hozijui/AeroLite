// eslint-disable-next-line
import { UserLayout, BasicLayout } from '@/layouts';

const RouteView = {
  name: 'RouteView',
  render: h => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.home' },
    redirect: '/workspace',
    children: [
      {
        path: '/workspace',
        name: 'Workspace',
        component: () => import('@/views/workspace/Workspace'),
        meta: { title: 'menu.workspace', keepAlive: true, icon: 'build', roles: ['admin', 'user'] }
      },
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Dashboard'),
        meta: { title: 'menu.dashboard', keepAlive: true, icon: 'dashboard', roles: ['admin', 'user'] }
      },
      {
        path: '/chart',
        name: 'Chart',
        meta: { title: 'menu.chart', keepAlive: true, icon: 'bar-chart', roles: ['admin', 'user'] }
      },
      {
        path: '/project',
        name: 'Project',
        meta: { title: 'menu.project', keepAlive: true, icon: 'apartment', roles: ['admin', 'user'] }
      },
      {
        path: '/algo',
        name: 'Algo',
        redirect: '/algo/manage',
        component: RouteView,
        meta: { title: 'menu.algo', keepAlive: true, icon: 'appstore', roles: ['admin', 'user'] },
        children: [
          {
            path: '/algo/manage',
            name: 'Component',
            meta: { title: 'menu.algo.manage', keepAlive: false, roles: ['admin', 'user'] }
          },
          {
            path: '/algo/rule',
            name: 'Rule',
            meta: { title: 'menu.algo.rule', keepAlive: false, roles: ['admin', 'user'] }
          },
          {
            path: '/algo/valve',
            name: 'Valve',
            meta: { title: 'menu.algo.valve', keepAlive: false, roles: ['admin', 'user'] }
          }
        ]
      },
      {
        path: '/data',
        name: 'Data',
        redirect: '/data/datasource',
        component: RouteView,
        meta: { title: 'menu.data', keepAlive: true, icon: 'database', roles: ['admin', 'user'] },
        children: [
          {
            path: '/data/datasource',
            name: 'Datasource',
            meta: { title: 'menu.data.datasource', keepAlive: false, roles: ['admin', 'user'] }
          },
          {
            path: '/data/dataset',
            name: 'Dataset',
            meta: { title: 'menu.data.dataset', keepAlive: false, roles: ['admin', 'user'] }
          }
        ]
      },
      {
        path: '/settings',
        name: 'Settings',
        redirect: '/settings/account',
        component: () => import('@/views/settings/Index'),
        hidden: true,
        meta: { hideHeader: true, keepAlive: true, roles: ['admin', 'user'] },
        children: [
          {
            path: '/settings/account',
            name: 'Account',
            component: () => import('@/views/settings/Account'),
            meta: { title: 'settings.account', keepAlive: false, roles: ['admin', 'user'] }
          },
          {
            path: '/settings/audit',
            name: 'UserAudit',
            component: () => import('@/views/settings/UserAudit'),
            meta: { title: 'settings.audit', keepAlive: false, roles: ['admin', 'user'] }
          },
          {
            path: '/settings/notification',
            name: 'Notification',
            component: () => import('@/views/settings/Notification'),
            meta: { title: 'settings.notification', keepAlive: false, roles: ['admin', 'user'] }
          }
        ]
      },
      {
        path: '/admin',
        name: 'Admin',
        hidden: true,
        meta: { roles: ['admin'] }
      },
      {
        path: '/about',
        name: 'About',
        hidden: true,
        meta: { roles: ['admin', 'user'] }
      },
      {
        path: '/error',
        name: 'error',
        component: RouteView,
        redirect: '/error/404',
        hidden: true,
        children: [
          {
            path: '/error/403',
            name: '403',
            component: () => import(/* webpackChunkName: "error" */ '@/views/exception/403'),
            meta: { title: '403' }
          },
          {
            path: '/error/404',
            name: '404',
            component: () => import(/* webpackChunkName: "error" */ '@/views/exception/404'),
            meta: { title: '404' }
          },
          {
            path: '/error/500',
            name: '500',
            component: () => import(/* webpackChunkName: "error" */ '@/views/exception/500'),
            meta: { title: '500' }
          }
        ]
      }
    ]
  },
  {
    path: '*',
    redirect: '/error',
    hidden: true
  }
]

export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      }
    ]
  }
]

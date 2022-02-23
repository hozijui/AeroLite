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
        meta: { title: 'menu.workspace', keepAlive: true, icon: 'build' }
      },
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Dashboard'),
        meta: { title: 'menu.dashboard', keepAlive: true, icon: 'dashboard' }
      },
      {
        path: '/chart',
        name: 'Chart',
        meta: { title: 'menu.chart', keepAlive: true, icon: 'bar-chart' }
      },
      {
        path: '/project',
        name: 'Project',
        meta: { title: 'menu.project', keepAlive: true, icon: 'apartment' }
      },
      {
        path: '/algo',
        name: 'Algo',
        redirect: '/algo/manage',
        component: RouteView,
        meta: { title: 'menu.algo', keepAlive: true, icon: 'appstore' },
        children: [
          {
            path: '/algo/manage',
            name: 'Component',
            meta: { title: 'menu.algo.manage', keepAlive: false }
          },
          {
            path: '/algo/rule',
            name: 'Rule',
            meta: { title: 'menu.algo.rule', keepAlive: false }
          },
          {
            path: '/algo/valve',
            name: 'Valve',
            meta: { title: 'menu.algo.valve', keepAlive: false }
          }
        ]
      },
      {
        path: '/data',
        name: 'Data',
        redirect: '/data/datasource',
        component: RouteView,
        meta: { title: 'menu.data', keepAlive: true, icon: 'database' },
        children: [
          {
            path: '/data/datasource',
            name: 'Datasource',
            meta: { title: 'menu.data.datasource', keepAlive: false }
          },
          {
            path: '/data/dataset',
            name: 'Dataset',
            meta: { title: 'menu.data.dataset', keepAlive: false }
          }
        ]
      },
      {
        path: '/settings',
        name: 'Settings',
        redirect: '/settings/account',
        component: () => import('@/views/settings/Index'),
        hidden: true,
        meta: { hideHeader: true, keepAlive: true },
        children: [
          {
            path: '/settings/account',
            name: 'Account',
            meta: { title: 'settings.account', keepAlive: false }
          },
          {
            path: '/settings/notification',
            name: 'Notification',
            meta: { title: 'settings.notification', keepAlive: false }
          }
        ]
      },
      {
        path: '/admin',
        name: 'Admin',
        hidden: true
      },
      {
        path: '/about',
        name: 'About',
        hidden: true
      },
      {
        path: '/exception',
        name: 'exception',
        component: RouteView,
        redirect: '/exception/404',
        hidden: true,
        children: [
          {
            path: '/exception/403',
            name: '403',
            component: () => import(/* webpackChunkName: "exception" */ '@/views/exception/403'),
            meta: { title: '403' }
          },
          {
            path: '/exception/404',
            name: '404',
            component: () => import(/* webpackChunkName: "exception" */ '@/views/exception/404'),
            meta: { title: '404' }
          },
          {
            path: '/exception/500',
            name: '500',
            component: () => import(/* webpackChunkName: "exception" */ '@/views/exception/500'),
            meta: { title: '500' }
          }
        ]
      }
    ]
  },
  {
    path: '*',
    redirect: '/exception',
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

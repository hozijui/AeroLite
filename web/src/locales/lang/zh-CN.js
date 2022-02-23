import antd from 'ant-design-vue/es/locale-provider/zh_CN'
import momentCN from 'moment/locale/zh-cn'
import global from './zh-CN/global'

import menu from './zh-CN/menu'
import settings from './zh-CN/settings'
import user from './zh-CN/user'
import dashboard from './zh-CN/dashboard'

const components = {
  antLocale: antd,
  momentName: 'zh-cn',
  momentLocale: momentCN
}

export default {
  message: '-',
  'layouts.usermenu.dialog.title': '信息',
  'layouts.usermenu.dialog.content': '您确定要注销吗？',
  'layouts.userLayout.title': '可视化辅助决策平台',
  ...components,
  ...global,
  ...menu,
  ...settings,
  ...user,
  ...dashboard
}

import storage from 'store'
import { login, getInfo, logout, refresh } from '@/api/auth'
import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    refresh_token: '',
    nickname: '',
    welcome: '',
    roles: [],
    permissions: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refresh_token = token
    },
    SET_NICKNAME: (state, { nickname, welcome }) => {
      state.nickname = nickname
      state.welcome = welcome
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const result = response.data
          storage.set(ACCESS_TOKEN, result.token, 30 * 60 * 1000)
          storage.set(REFRESH_TOKEN, result.refresh_token, 360 * 60 * 1000)
          commit('SET_TOKEN', result.token)
          commit('SET_REFRESH_TOKEN', result.refresh_token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    RefreshToken ({ commit }) {
      return new Promise((resolve, reject) => {
        const token = storage.get(REFRESH_TOKEN)
        refresh(token).then(response => {
          const result = response.data
          storage.set(ACCESS_TOKEN, result.token || '', 30 * 60 * 1000)
          commit('SET_TOKEN', result.token || '')
          resolve()
        }).catch(() => {
          storage.set(ACCESS_TOKEN, '')
          commit('SET_TOKEN', '')
          resolve()
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const result = response.data
          if (result.roles && result.roles.length > 0) {
            commit('SET_ROLES', result.roles)
            commit('SET_PERMISSIONS', result.permissions)
            commit('SET_INFO', result)
          } else {
            reject(new Error('getInfo: roles must be a non-null array !'))
          }
          commit('SET_NICKNAME', { nickname: result.nickname, welcome: welcome() })
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_REFRESH_TOKEN', '')
          commit('SET_ROLES', [])
          storage.remove(ACCESS_TOKEN)
          storage.remove(REFRESH_TOKEN)
          resolve()
        }).catch((err) => {
          console.log('logout fail:', err)
          // resolve()
        }).finally(() => {
        })
      })
    }

  }
}

export default user

import request from '@/utils/request'
import qs from 'qs'

const authApi = {
  Login: '/auth/login',
  Logout: '/auth/logout',
  RefreshToken: '/auth/refresh',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  UserInfo: '/user/info'
}

export function login (parameter) {
  return request({
    url: authApi.Login,
    method: 'post',
    data: qs.stringify(parameter),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function getInfo () {
  return request({
    url: authApi.UserInfo,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function refresh (token) {
  return request({
    url: authApi.RefreshToken,
    method: 'post',
    data: qs.stringify({ refreshToken: token }),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function logout () {
  return request({
    url: authApi.Logout,
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

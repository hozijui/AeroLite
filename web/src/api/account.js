import request from '@/utils/request'
import qs from 'qs'

const accountApi = {
  UserInfo: '/account/info',
  ModifyPassword: '/account/password'
}

export function getInfo () {
  return request({
    url: accountApi.UserInfo,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function updateInfo (payload) {
  return request({
    url: accountApi.UserInfo,
    method: 'patch',
    data: qs.stringify(payload),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function modifyPassword (payload) {
  return request({
    url: accountApi.ModifyPassword,
    method: 'put',
    data: qs.stringify(payload),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

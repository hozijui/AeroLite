import request from '@/utils/request'
import qs from 'qs'

const adminApi = {
  user: '/user',
  role: '/role'
}

export default adminApi

export function getUserList (parameter) {
  return request({
    url: adminApi.user,
    method: 'get',
    params: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function createUser (payload) {
  return request({
    url: adminApi.user,
    method: 'post',
    data: qs.stringify(payload),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function updateUser (payload) {
  const id = payload.id
  delete payload.id
  return request({
    url: `${adminApi.user}/${id}`,
    method: 'patch',
    data: qs.stringify(payload),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function modifyUserPassword (id, payload) {
  return request({
    url: `${adminApi.user}/${id}/password`,
    method: 'put',
    data: qs.stringify(payload),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
  })
}

export function deleteUser (id) {
  return request({
    url: `${adminApi.user}/${id}`,
    method: 'delete'
  })
}

export function getRoleList () {
  return request({
    url: adminApi.role,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

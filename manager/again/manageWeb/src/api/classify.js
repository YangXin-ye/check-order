import request from '@/utils/request'
import http from "../utils/request";

export function getClassifyList(params) {
  return http.get('/classify/list', params)
}

export function insertClassify(data) {
  return http.post('/classify/insert', data)
}

export function updateClassify(data) {
  return http.post('/classify/update', data)
}

export function deleteClassify(id) {
  return http.get('/classify/delete?id='+id)
}

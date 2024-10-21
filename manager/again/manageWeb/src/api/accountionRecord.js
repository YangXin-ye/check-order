import http from "../utils/request";

export function getAccountionRecords(params) {
  return http.get('/accountion/record/list', params)
}

export function exportAccountionRecords(params) {
  return http({
    url: '/accountion/record/export',
    method: 'get',
    params: params,
    responseType: 'blob' // This is important for binary files
  })
}


export function getDelAccountionRecords(params) {
  return http.get('/accountion/record/del/list', params)
}
export function restoreData(dataId){
  return http.get('/accountion/record/restore?id='+dataId)
}

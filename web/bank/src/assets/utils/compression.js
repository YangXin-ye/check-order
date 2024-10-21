/*
 * @Descripttion: 图片压缩
 * @Author: jiansheng
 */ 

function convertBase64ToBlob(data) {
  let arr = data.split(','),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n)
  while(n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }
  return new Blob([u8arr], {
    type: mime
  })
}

function canvasDataUrl(path, obj, cb) {
  let img = new Image()
  img.src = path
  img.onload = function () {
    let that = this
    let w = that.width,
      h = that.height,
      scale = w / h
    w = obj.width || w
    h = obj.height || (w / scale)
    let quality = 0.7               // 默认图片质量为0.7
    //生成canvas
    let canvas = document.createElement('canvas')
    let ctx = canvas.getContext('2d');
    // 创建属性节点
    let anw = document.createAttribute("width")
    anw.nodeValue = w
    let anh = document.createAttribute("height")
    anh.nodeValue = h
    canvas.setAttributeNode(anw)
    canvas.setAttributeNode(anh)
    ctx.drawImage(that, 0, 0, w, h)
    // 图像质量
    if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
      quality = obj.quality
    }
    // quality值越小，所绘制出的图像越模糊
    let base64 = canvas.toDataURL('image/jpeg', quality)
    
    cb(base64)  // 回调函数返回base64的
  }
}

export const imgCompress = (file, w, objDiv) => {
  let ready = new FileReader()
  ready.readAsDataURL(file)
  ready.onload = function() {
    let res = this.result
    canvasDataUrl(res, w, objDiv)
  }
}

/**
 * @param {file} _a file对象 
 * @param {Number} quality 压缩比例
 * @param {Number} minSize 限定超过多大的进行压缩 单位 M
 */
export const compression = (_a, quality = 0.4, minSize = 0.15, fn) => {
  const _s = _a.size / 1024 / 1024
  console.log('压缩前' ,_a.size / 1024 / 1024)
  if (_s > minSize) {
    imgCompress(_a, { quality }, function (base64Data) {
      let _b = convertBase64ToBlob(base64Data)
      fn && fn(_b)
      console.log('压缩后', _b.size / 1024 / 1024)
      // return _b
    })
  }else{
    fn && fn(_a)
  }
}
// Query list interface
const getCategoryPage = (params) => {
  return $axios({
    url: '/category/page',
    method: 'get',
    params
  })
}

// Edit the reverse query details of the page
const queryCategoryById = (id) => {
  return $axios({
    url: `/category/${id}`,
    method: 'get'
  })
}

// Delete the interface of the current column
const deleCategory = (ids) => {
  return $axios({
    url: '/category',
    method: 'delete',
    params: { ids }
  })
}

// Modify the interface
const editCategory = (params) => {
  return $axios({
    url: '/category',
    method: 'put',
    data: { ...params }
  })
}

// Added APIs
const addCategory = (params) => {
  return $axios({
    url: '/category',
    method: 'post',
    data: { ...params }
  })
}
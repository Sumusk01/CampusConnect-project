import axios from 'axios'

const api = axios.create({ baseURL: '/api' })

export const getStudents = async () => {
  const res = await api.get('/students')
  return res.data
}

export const createStudent = async (student: any) => {
  const res = await api.post('/students', student)
  return res.data
}

export const updateStudent = async (id: number, student: any) => {
  const res = await api.put(`/students/${id}`, student)
  return res.data
}

export const deleteStudent = async (id: number) => {
  await api.delete(`/students/${id}`)
}
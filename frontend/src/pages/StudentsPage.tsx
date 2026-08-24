import React, { useEffect, useState } from 'react'
import { getStudents, deleteStudent } from '../api/students'
import CreateStudentForm from '../components/CreateStudentForm'
import EditStudentModal from '../components/EditStudentModal'

export default function StudentsPage() {
  const [students, setStudents] = useState<any[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [editing, setEditing] = useState<any | null>(null)

  const load = async () => {
    setLoading(true)
    setError(null)
    try {
      const data = await getStudents()
      setStudents(data)
    } catch (e: any) {
      setError(e?.response?.data?.message || e.message || 'Failed to load students')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [])

  const handleCreated = (created: any) => {
    // reload list from server to keep data consistent
    load()
  }

  const handleDelete = async (id: number) => {
    if (!confirm('Delete this student?')) return
    try {
      await deleteStudent(id)
      setStudents(prev => prev.filter(s => s.id !== id))
    } catch (err: any) {
      alert(err?.response?.data?.message || err.message || 'Delete failed')
    }
  }

  const openEdit = (student: any) => {
    setEditing(student)
  }

  const closeEdit = () => {
    setEditing(null)
  }

  const handleUpdated = (updated: any) => {
    // update local list without full reload
    setStudents(prev => prev.map(s => (s.id === updated.id ? updated : s)))
  }

  if (loading) return <div>Loading students…</div>
  if (error) return <div style={{ color: 'crimson' }}>Error: {error}</div>

  return (
    <div style={{ padding: 16 }}>
      <h2 style={{ marginBottom: 12 }}>Students</h2>

      <CreateStudentForm onCreated={handleCreated} />

      {students.length === 0 ? (
        <div>No students yet</div>
      ) : (
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
          <thead>
            <tr>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>ID</th>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>Name</th>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>Age</th>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>Course</th>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>Email</th>
              <th style={{ border: '1px solid #ddd', padding: 8 }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map((s: any) => (
              <tr key={s.id}>
                <td style={{ border: '1px solid #eee', padding: 8 }}>{s.id}</td>
                <td style={{ border: '1px solid #eee', padding: 8 }}>{s.name}</td>
                <td style={{ border: '1px solid #eee', padding: 8 }}>{s.age}</td>
                <td style={{ border: '1px solid #eee', padding: 8 }}>{s.course}</td>
                <td style={{ border: '1px solid #eee', padding: 8 }}>{s.email}</td>
                <td style={{ border: '1px solid #eee', padding: 8, width: 180 }}>
                  <button onClick={() => openEdit(s)} style={{ marginRight: 8 }}>Edit</button>
                  <button onClick={() => handleDelete(s.id)} style={{ color: 'crimson' }}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <EditStudentModal student={editing} onClose={closeEdit} onUpdated={handleUpdated} />
    </div>
  )
}
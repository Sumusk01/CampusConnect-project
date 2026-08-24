import React, { useEffect, useState } from 'react'
import { updateStudent } from '../api/students'

type Props = {
  student: any | null
  onClose: () => void
  onUpdated: (updated: any) => void
}

export default function EditStudentModal({ student, onClose, onUpdated }: Props) {
  const [name, setName] = useState('')
  const [age, setAge] = useState<number | ''>('')
  const [course, setCourse] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    if (student) {
      setName(student.name || '')
      setAge(student.age ?? '')
      setCourse(student.course || '')
      setEmail(student.email || '')
      setError(null)
    }
  }, [student])

  if (!student) return null

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    if (!name.trim()) return setError('Name is required')
    if (!age || Number(age) <= 0) return setError('Valid age is required')
    if (!course.trim()) return setError('Course is required')
    if (!email.includes('@')) return setError('Valid email is required')

    setLoading(true)
    try {
      const payload = { name: name.trim(), age: Number(age), course: course.trim(), email: email.trim() }
      const updated = await updateStudent(student.id, payload)
      onUpdated(updated)
      onClose()
    } catch (err: any) {
      setError(err?.response?.data?.message || err.message || 'Update failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{
      position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.35)',
      display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 9999
    }}>
      <form onSubmit={handleSubmit} style={{ background: '#fff', padding: 20, borderRadius: 8, width: 520, maxWidth: '95%' }}>
        <h3 style={{ marginTop: 0 }}>Edit Student</h3>

        <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap' }}>
          <input placeholder="Name" value={name} onChange={e => setName(e.target.value)} style={{ flex: '1 1 200px', padding: 8 }} />
          <input placeholder="Age" value={age} onChange={e => setAge(e.target.value === '' ? '' : Number(e.target.value))} style={{ width: 100, padding: 8 }} />
          <input placeholder="Course" value={course} onChange={e => setCourse(e.target.value)} style={{ flex: '1 1 150px', padding: 8 }} />
          <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} style={{ flex: '1 1 220px', padding: 8 }} />
        </div>

        {error && <div style={{ color: 'crimson', marginTop: 8 }}>{error}</div>}

        <div style={{ marginTop: 12, display: 'flex', gap: 8, justifyContent: 'flex-end' }}>
          <button type="button" onClick={onClose} style={{ padding: '8px 12px' }} disabled={loading}>Cancel</button>
          <button type="submit" style={{ padding: '8px 12px' }} disabled={loading}>
            {loading ? 'Saving…' : 'Save Changes'}
          </button>
        </div>
      </form>
    </div>
  )
}
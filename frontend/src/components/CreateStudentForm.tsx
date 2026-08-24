import React, { useState } from 'react'
import { createStudent } from '../api/students'

type Props = {
  onCreated: (created: any) => void
}

export default function CreateStudentForm({ onCreated }: Props) {
  const [name, setName] = useState('')
  const [age, setAge] = useState<number | ''>('')
  const [course, setCourse] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const reset = () => {
    setName('')
    setAge('')
    setCourse('')
    setEmail('')
    setError(null)
  }

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
      const created = await createStudent(payload)
      onCreated(created)
      reset()
    } catch (err: any) {
      setError(err?.response?.data?.message || err.message || 'Failed to create student')
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: 16, border: '1px solid #eee', padding: 12, borderRadius: 6 }}>
      <h3 style={{ marginTop: 0 }}>Add Student</h3>

      <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap' }}>
        <input placeholder="Name" value={name} onChange={e => setName(e.target.value)} style={{ flex: '1 1 200px', padding: 8 }} />
        <input placeholder="Age" value={age} onChange={e => setAge(e.target.value === '' ? '' : Number(e.target.value))} style={{ width: 100, padding: 8 }} />
        <input placeholder="Course" value={course} onChange={e => setCourse(e.target.value)} style={{ flex: '1 1 150px', padding: 8 }} />
        <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} style={{ flex: '1 1 220px', padding: 8 }} />
      </div>

      {error && <div style={{ color: 'crimson', marginTop: 8 }}>{error}</div>}

      <div style={{ marginTop: 10 }}>
        <button type="submit" disabled={loading} style={{ padding: '8px 12px' }}>
          {loading ? 'Adding…' : 'Add Student'}
        </button>
      </div>
    </form>
  )
}
import React from 'react'
import { Routes, Route, Link } from 'react-router-dom'
import StudentsPage from './pages/StudentsPage'

export default function App() {
  return (
    <div style={{ padding: 20 }}>
      <header style={{ marginBottom: 16 }}>
  <div className="app-header">
    <div className="app-title">
      <h1>CampusConnect</h1>
      <div className="app-subtitle">Student Information System</div>
    </div>

    <nav className="app-nav">
      <Link to="/" className="nav-link">Student Directory</Link>
    </nav>
  </div>
</header>

      <main>
        <Routes>
          <Route path="/" element={<StudentsPage />} />
        </Routes>
      </main>
    </div>
  )
}
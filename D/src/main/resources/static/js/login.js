// login.js
// Handles login and redirects based on user role

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (!loginForm) return;

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const role = document.getElementById('role').value;
        const department = document.getElementById('department') ? document.getElementById('department').value : '';
        let payload = { role };
        if (role === 'student') {
            payload.username = document.getElementById('username').value;
            payload.password = document.getElementById('password').value;
            payload.department = department;
        } else if (role === 'teacher') {
            payload.email = document.getElementById('email').value;
            payload.passwordTeacher = document.getElementById('passwordTeacher').value;
            payload.department = department;
        } else if (role === 'admin') {
            payload.usernameAdmin = document.getElementById('usernameAdmin').value;
            payload.passwordAdmin = document.getElementById('passwordAdmin').value;
        }

        fetch('/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success && data.role === 'STUDENT') {
                window.location.href = '/student_dashboard';
            } else if (data.success && data.role === 'TEACHER') {
                window.location.href = '/teacher_dashboard';
            } else if (data.success && data.role === 'ADMIN') {
                window.location.href = '/admin_dashboard';
            } else {
                document.getElementById('errorMsg').innerText = data.message || 'Login failed';
            }
        })
        .catch(() => {
            document.getElementById('errorMsg').innerText = 'Server error. Please try again.';
        });
    });
});

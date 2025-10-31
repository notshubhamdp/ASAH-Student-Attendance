// Password strength checker
function checkPasswordStrength(password) {
    let strengthBar = document.getElementById('strengthBar');
    let strengthText = document.getElementById('strengthText');
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;

    // Set bar and text only if elements exist
    if (strengthBar && strengthText) {
        switch(strength) {
            case 0:
            case 1:
                strengthBar.style.width = '20%';
                strengthBar.style.background = '#f87171';
                strengthText.textContent = 'Weak';
                break;
            case 2:
            case 3:
                strengthBar.style.width = '60%';
                strengthBar.style.background = '#fbbf24';
                strengthText.textContent = 'Medium';
                break;
            case 4:
            case 5:
                strengthBar.style.width = '100%';
                strengthBar.style.background = '#22c55e';
                strengthText.textContent = 'Strong';
                break;
        }
    }
}

// Username uniqueness checker (simulate with local array, replace with AJAX in production)
const existingUsernames = ['john', 'jane', 'admin', 'teacher']; // Example, replace with backend check
function checkUsernameUnique() {
    let usernameInput = document.getElementById('username');
    let usernameMsg = document.getElementById('usernameMsg');
    let username = usernameInput.value.trim().toLowerCase();
    if (existingUsernames.includes(username)) {
        usernameMsg.textContent = 'Username already taken';
        usernameMsg.style.color = '#f87171';
    } else if (username.length < 4) {
        usernameMsg.textContent = 'Username too short';
        usernameMsg.style.color = '#fbbf24';
    } else {
        usernameMsg.textContent = 'Username available';
        usernameMsg.style.color = '#22c55e';
    }
}

// AJAX registration logic to ensure role and status are set correctly
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    if (!form) return;
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const data = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            username: document.getElementById('username').value,
            department: document.getElementById('department').value,
            role: document.getElementById('role').value.toUpperCase(), // Ensure enum matches backend
            password: document.getElementById('password').value
        };
        fetch('/api/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => response.json().then(res => ({ status: response.status, body: res })))
        .then(({ status, body }) => {
            const errorDiv = document.getElementById('registerErrorMsg');
            errorDiv.textContent = '';
            if (status === 200 && body.message.includes('successfully')) {
                window.location.href = '/login';
            } else {
                errorDiv.textContent = body.message || 'Registration failed. Please try again.';
            }
        })
        .catch(() => {
            const errorDiv = document.getElementById('registerErrorMsg');
            errorDiv.textContent = 'Registration failed. Please try again.';
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('password').addEventListener('input', function(e) {
        checkPasswordStrength(e.target.value);
    });
    document.getElementById('username').addEventListener('input', function() {
        checkUsernameUnique();
    });
});

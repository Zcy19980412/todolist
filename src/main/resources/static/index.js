function signIn() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // 简单的验证
        if (username && password) {
            alert(`Sign In with username: ${username} and password: ${password}`);
            // 在这里添加你的登录逻辑，比如调用后端API
        } else {
            alert('Please enter both username and password.');
        }
    }

    <!--  注册  -->
    function signUp() {
        const username = document.getElementById('username').value;
        const realName = document.getElementById('realName').value;
        const password = document.getElementById('password').value;

        if (username && realName && password) {
            const user = {
                username: username,
                realName: realName,
                password: password
            };

            fetch('http://localhost:8080/user/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
            .then(response => response.json())
            .then(data => {
                const message = data.message;
                if(data.success){
                    alert('Registration successful!');
                } else{
                    alert(`Registration failed: ${message}`);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while registering.');
            });
        } else {
            alert('Please fill out all fields.');
        }
    }
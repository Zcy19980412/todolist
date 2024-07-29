import {config} from "./globalConfig.js";


window.signIn = signIn;
window.signUp = signUp;


function signIn() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 简单的验证
    if (username && password) {
        const user = {
                username: username,
                password: password
                };
        // 在这里添加你的登录逻辑，比如调用后端API
        fetch(config.url + '/security/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(user)
                })
                .then(response => {
                    console.log(response);
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    // 获取token
                    const token = response.headers.get('Authorization');
                    if (token) {
                        // 存储token到localStorage
                        localStorage.setItem('token', token);
                        alert('Login successful!');
                        return response.json();
                    } else {
                        alert('Token not found in response');
                        throw new Error('Token not found in response');
                    }
                })
                .then(data => {
                    console.log('Success:', data);
                    // 跳转到另一个页面
                    window.location.href = config.url + '/habit.html'; // 替换为你想要跳转的页面URL
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while login.');
                });
    } else {
        alert('Please enter both username and password.');
    }
    //调用登录
}


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

        fetch(config.url + '/user/save', {
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

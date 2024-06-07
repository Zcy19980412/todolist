
//封装设置请求头携带token的方法
export function fetchWithToken(url, options = {}) {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('No token found, please log in first.');
        return Promise.reject('No token found');
    }


    // 确保请求头包含Authorization
    const headers = new Headers(options.headers || {});
    headers.set('Authorization', token); // 确保使用Bearer token格式


    const updatedOptions = {
        ...options,
        headers: headers,
        credentials: 'include'
    };


    return fetch(url, updatedOptions)
        .then(response => {
            if (response.status === 401) {
                alert('Token expired or invalid, please log in again.');
                localStorage.removeItem('token'); // 移除过期或无效的token
                return Promise.reject('Unauthorized');
            }
            return response.json();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while fetching data.');
            throw error;
        });
}

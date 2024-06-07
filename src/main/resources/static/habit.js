import {fetchWithToken} from "./utils.js";


function createHabit() {
    const habitName = document.getElementById('habit').value;
    const description = document.getElementById('description').value;
    const gapDays = document.getElementById('gapDays').value;
    const importantRate = document.getElementById('importantRate').value;

    if (habitName && description && gapDays && importantRate ) {
        const habit = {
            name: habitName,
            description: description,
            gapDays: gapDays,
            importantRate: importantRate
        };

        fetchWithToken('http://localhost:8080/habit/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(habit)
        })
        .then(data => {
            if(data.success){
                alert('create habit successful!');
            } else{
                const message = data.message;
                alert(`create habit failed: ${message}`);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating habit.');
        });
    } else {
        alert('Please fill out all fields.');
    }
}

// 将createHabit函数挂载到window对象上
window.createHabit = createHabit;

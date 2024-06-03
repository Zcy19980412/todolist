function createHabit() {
    const habitName = document.getElementById('habit').value;
    const description = document.getElementById('description').value;
    const gapDays = document.getElementById('gapDays').value;
    const importantRate = document.getElementById('importantRate').value;
    const userId = document.getElementById('userId').value;

    if (habitName && description && gapDays && importantRate && userId) {
        const habit = {
            name: habitName,
            description: description,
            gapDays: gapDays,
            importantRate: importantRate,
            userId: userId
        };

        fetch('http://localhost:8080/habit/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(habit)
        })
        .then(response => response.json())
        .then(data => {
            const message = data.message;
            if(data.success){
                alert('create habit successful!');
            } else{
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

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
                getHabitList();
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
// 将createHabit函数挂载到window对象上
window.getHabitList = getHabitList;

function getHabitList() {
    fetchWithToken('http://localhost:8080/habit/list', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if(!response.success){
            const message = response.message;
            alert(`create habit failed: ${message}`);
        }
        const message = response.message;
        const tableBody = document.querySelector("#habitList tbody");
        tableBody.innerHTML = '';
        message.forEach(habit=>{
            const row = document.createElement('tr');
            const cellImportantRate = document.createElement("td");
            const cellName = document.createElement("td");
            const cellCreateTime = document.createElement("td");
            const cellDescription = document.createElement("td");
            const cellDoneRate = document.createElement("td");
            const cellTodo = document.createElement("td");
            const cellDelete = document.createElement("td");

            const deleteHabitButton = document.createElement("button");
            deleteHabitButton.innerText = "删除";
            deleteHabitButton.setAttribute("habitId", habit.id); // 设置自定义属性存储习惯的 ID
            deleteHabitButton.addEventListener("click", handleDeleteHabit); // 添加点击事件处理程序

            const todoHabitButton = document.createElement("button");
            todoHabitButton.innerText = "打卡";
            todoHabitButton.setAttribute("habitId", habit.id); // 设置自定义属性存储习惯的 ID
            todoHabitButton.addEventListener("click", handleTodoHabit); // 添加点击事件处理程序


            cellImportantRate.textContent = habit.importantRate;
            cellName.textContent = habit.name;
            cellCreateTime.textContent = habit.createTime;
            cellDescription.textContent = habit.description;
            cellDoneRate.textContent = habit.doneRate;
            cellTodo.appendChild(todoHabitButton);
            cellDelete.appendChild(deleteHabitButton);
            row.appendChild(cellImportantRate);
            row.appendChild(cellName);
            row.appendChild(cellCreateTime);
            row.appendChild(cellDescription);
            row.appendChild(cellDoneRate);
            row.appendChild(cellTodo);
            row.appendChild(cellDelete);
            tableBody.appendChild(row);
        })
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while list habit.');
    });
}


function handleDeleteHabit(event) {
    const habitId = event.target.getAttribute("habitId"); // 获取存储在按钮上的习惯 ID
    fetchWithToken("http://localhost:8080/habit/delete?id=" + habitId, {
            method: 'GET'
        })
        .then(response => {
            if (response.success) {
                getHabitList();
            } else {
                const message = response.message;
                console.log(message);
                alert("An Error Accrued while deleting habit.");
            }
        }).catch(reason => {
        console.error('Error:', reason);
    })
}

function handleTodoHabit(event) {
    const habitId = event.target.getAttribute("habitId"); // 获取存储在按钮上的习惯 ID

}

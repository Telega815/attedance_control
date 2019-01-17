$(document).ready(function () {
    getDepartments();
});

function getAttendance(){
    var obj;

    var minDate = document.getElementById("inputEmpMin").value.split("-");
    var maxDate = document.getElementById("inputEmpMax").value.split("-");

    var minDayT = minDate[2];
    var minMonthT = minDate[1];
    var minYearT = minDate[0];

    var maxDayT = maxDate[2];
    var maxMonthT = maxDate[1];
    var maxYearT = maxDate[0];

    obj = {
        selectedEmployee: true,
        empId:selectedEmployeeId,
        minDay: minDayT,
        maxDay: maxDayT,
        minMonth: minMonthT-1,
        maxMonth: maxMonthT-1,
        minYear: minYearT,
        maxYear: maxYearT
    };


    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/restService/getRightPanelContent?"+csrfParameter+"="+csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(data){
            var rb = document.getElementById("adminAttendanceList");
            rb.innerHTML = data.respHtml;
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function onchangeTest(event) {
    var selectedId = event.target.selectedOptions.item(0).id.split("_")[1];

    var obj;
    obj = {
        depId: selectedId
    };
    var data = JSON.stringify(obj);
    var docURL = window.location.protocol + "//" + window.location.host + "/admin/restService/getEmployees?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function (data) {
            var empList = document.getElementById("adminEmployeeList");
            empList.innerHTML = "";
            for (var i in data.deps){
                var li = document.createElement("li");
                li.id = "employeeLi_" + i;
                li.value = data.deps[i];
                li.textContent = data.deps[i];
                empList.appendChild(li);
            }
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}


function getDepartments() {
    var docURL = window.location.protocol + "//" + window.location.host + "/admin/restService/getDepartments?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        success: function (data) {
            var select = document.getElementById("selectDepartment");
            select.innerHTML = "";

            for (var i in data.deps){
                var option = document.createElement("option");
                option.id = "depOption_" + i;
                option.value = data.deps[i];
                option.textContent = data.deps[i];
                select.appendChild(option);
            }

            var ev = {
                target: select
            };
            onchangeTest(ev);
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function createEmployee() {
    var obj;

    var select = document.getElementById("selectDepartment");
    var inputs = document.getElementsByClassName("AdminEmployeeInput");
    obj = {
        depId: select.selectedOptions.item(0).id.split("_")[1],
        surname: inputs[0].value,
        name: inputs[1].value,
        patronymic: inputs[2].value,
        key: inputs[3].value
    };

    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/createEmployee?"+csrfParameter+"="+csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(){
            var successLabel = document.getElementById("successLabel");
            successLabel.style.display = "block";
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function createDepartment() {
    var obj;

    var newDepInput = document.getElementById("newDepName");

    obj = {
        newDepName: newDepInput.value
    };

    var data = JSON.stringify(obj);

    var docURL = window.location.protocol + "//" + window.location.host + "/admin/restService/createEmployee?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function (data) {
            var successLabel = document.getElementById("successLabel");
            successLabel.style.display = "block";

            var select = document.getElementById("selectDepartment");
            var option = document.createElement("option");
            option.id = "depOption_" + data.newDepId;
            option.value = obj.newDepName;
            option.textContent = obj.newDepName;
            select.appendChild(option);
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}
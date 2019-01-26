var selectedEmployee;
var selectedAttendance;

$(document).ready(function () {
    getDepartments();
});

function selectEmployee(event){
    var empLi = document.getElementsByClassName("employeeLi");
    for(var i =0 ; i < empLi.length; i++){
        empLi[i].style.backgroundColor = "white";
    }
    selectedEmployee = event.target;
    selectedEmployee.style.backgroundColor = "rgb(0,255,178)";


    var obj = {
        empId: selectedEmployee.id.split("_")[1]
    };

    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/getEmpKey?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(data){
            document.getElementById("inputKeyField").value = data;
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function saveNewKey(){
    var empKey = document.getElementById("inputKeyField");

    var obj = {
        empId: selectedEmployee.id.split("_")[1],
        empKey: empKey.value
    };

    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/setEmpKey?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(){
            successAnimation();
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function writeAttendance() {
    var dateTime = document.getElementById("adminAddAttendanceInput").value.split("T");
    var date = dateTime[0].split("-");
    var time = dateTime[1].split(":");

    var obj = {
        emplId: selectedEmployee.id.split("_")[1],
        day: date[2],
        month: date[1]-1,
        year: date[0],

        hour:  time[0],
        minute: time[1]
    };

    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/writeAttendance?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(){
            getAttendance();
            successAnimation();
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function getAttendance(){
    if (selectedEmployee == null)
        return;

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
        empId: selectedEmployee.id.split("_")[1],
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
                li.classList.add("employeeLi");
                li.onclick = selectEmployee;
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
            var select2 = document.getElementById("selectDepartment2");
            select2.innerHTML = "";
            select.innerHTML = "";

            for (var i in data.deps){
                var option = document.createElement("option");
                option.id = "depOption_" + i;
                option.value = data.deps[i];
                option.textContent = data.deps[i];
                select.appendChild(option);

                var option2 = document.createElement("option");
                option2.id = "depOptionSel2_" + i;
                option2.value = data.deps[i];
                option2.textContent = data.deps[i];
                select2.appendChild(option2);
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
            successAnimation();
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

    var docURL = window.location.protocol + "//" + window.location.host + "/admin/restService/createDepartment?" + csrfParameter + "=" + csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function (data) {
            successAnimation();

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


function getAttendanceOfDay() {
    if (selectedEmployee == null)
        return;

    var obj;

    var date = document.getElementById("inputAttendanceOfDay").value.split("-");

    var minDayT = date[2];
    var minMonthT = date[1];
    var minYearT = date[0];

    obj = {
        selectedEmployee: true,
        empId: selectedEmployee.id.split("_")[1],
        minDay: minDayT,
        minMonth: minMonthT-1,
        minYear: minYearT
    };


    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/getAttendanceOfDay?"+csrfParameter+"="+csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(data){
            var empList = document.getElementById("adminAttendanceOfDay");
            empList.innerHTML = "";
            var list = [];
            for (var i in data.attendance){
                var li = document.createElement("li");
                li.id = "attendanceOfDay_" + i;
                li.classList.add("attendanceOfDayLi");
                li.onclick = selectAttendanceOfDay;
                //li.value = data.attendance[i];
                li.textContent = data.attendance[i];
                list.push(li);
            }
            list.sort(function (a , b) {
               var aH, bH, aM, bM;
               aH = a.textContent.split(":");
               aM = aH[1];
               aH = aH[0];
               bH = b.textContent.split(":");
               bM = bH[1];
               bH = bH[0];

               if (aH > bH) return 1;
               else if (aH < bH) return -1;
               else {
                   if (aM > bM) return 1;
                   else if (aM < bM) return -1;
                   else {
                        return 0;
                   }
               }
            });

            for (var i = 0; i < list.length;i++){
                empList.appendChild(list[i]);
            }
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function selectAttendanceOfDay() {
    var empLi = document.getElementsByClassName("attendanceOfDayLi");
    for(var i =0 ; i < empLi.length; i++){
        empLi[i].style.backgroundColor = "white";
    }
    selectedAttendance = event.target;
    selectedAttendance.style.backgroundColor = "rgb(0,255,178)";
}

function deleteAttendanceOfDay() {
    if (selectedAttendance == null)
        return;

    selectedAttendance.style.display = "none";

    var obj;
    obj = {
        empId: selectedAttendance.id.split("_")[1]
    };


    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/admin/restService/deleteAttendanceOfDay?"+csrfParameter+"="+csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(){
            selectedAttendance = null;
            successAnimation();
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}


function successAnimation() {
    $(".sa-success").addClass("hide");
    setTimeout(function() {
        $(".sa-success").removeClass("hide");
    }, 10);
    setTimeout(function() {
        $(".sa-success").addClass("hide");
    }, 2000);
}
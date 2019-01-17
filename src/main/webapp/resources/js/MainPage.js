var selectedDepartment;
var selectedEmployee = false;
var selectedEmployeeId;

var selectedMonth = -1;
var daysInMoth = -1;
var selectedYear = -1;

var minDate;
var maxDate;

$(document).ready(function () {
    var nodes = document.getElementsByClassName("departmentLi");
    var ev = {};
    ev.target = nodes[0];
    selectDepartment(ev);
});

function searchChanged( event ) {
    var searchField = event.target.value;
    var depList = document.getElementById("departments");
    var searchList = document.getElementById("searchList");
    searchList.innerHTML = "";

    if (searchField == "") {
        depList.style.display = "block";
        searchList.style.display = "none";
        return;
    }

    var departments = document.getElementsByClassName("depNameHiddenSpan");
    var employees = document.getElementsByClassName("employeeLi");

    var depRes = [];
    var empRes = [];

    for (var i = 0; i < departments.length; i++){
        if (departments[i].innerText.toUpperCase().includes(searchField.toUpperCase())){
            depRes.push(departments[i]);
        }
    }

    for (var i = 0; i < employees.length; i++){
        if (employees[i].innerText.toUpperCase().includes(searchField.toUpperCase())){
            empRes.push(employees[i]);
        }
    }


    for (var i = 0; i < depRes.length; i++){
        var li = document.createElement("li");
        li.innerText = depRes[i].textContent;
        li.style.font = "bold 14pt Segoe UI";
        searchList.appendChild(li);
    }
    for (var i = 0; i < empRes.length; i++){
        var li = document.createElement("li");
        li.innerText = empRes[i].textContent;
        li.style.font = "14pt Segoe UI";
        searchList.appendChild(li);
    }

    depList.style.display = "none";
    searchList.style.display = "block";
}

function printButtonClick() {
    window.print();
}

function showButtonClick() {
    getRightPanelContent();
}

function minDateChanged(event) {
    minDate = event.target.value;
}

function maxDateChanged(event) {
    maxDate = event.target.value;
}

function getRightPanelContent(){
    var obj;



    if (!selectedEmployee){
        var month = document.getElementById("selectMonth");
        var year = document.getElementById("selectYear");
        obj = {
            selectedEmployee: false,
            depId: selectedDepartment.id.split("_")[1],
            month: month.selectedIndex,
            year: year.value
        };
    }else {
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
    }



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
            var rb = document.getElementById("rightBlock");
            rb.innerHTML = data.respHtml;
            daysInMoth = data.dayInMonth;
            selectedMonth = data.month;
            selectedYear = data.year;
        },
        error: function (e) {
            alert("Error!!!\n" + e.responseText);
        }
    });
}

function anal() {
    var li = document.createElement("li");
    li.addClass("departmentLi");
    li.id = "departmentLi_1";
    li.onclick = selectDepartment(event);

    var ul = document.createElement("ul");
    ul.addClass("employees");
    ul.id = "employees_1";
    ul.style.display = "none";

    var liWorkers = {};
    liWorkers[0] = document.createElement("li");
}

function selectDepartment( event ){

    var node = event.target;
    var liName = document.getElementById("departmentName");

    if ((node != selectedDepartment || selectedEmployee) && !node.classList.contains("employeeLi")) {
        var nodeId = node.id.split("_")[1];
        var employees = document.getElementById("employees_"+ nodeId);

        if (employees != null) {
            employees.style.display = "block";
        }

        if (selectedDepartment != null) {
            selectedDepartment.style.color = "#707070";
            selectedDepartment.style.backgroundColor = "white";

            var id = selectedDepartment.id.split("_")[1];
            var employees = document.getElementById("employees_"+ id);
            if (selectedDepartment != node && employees != null) {
                employees.style.display = "none";
            }
            var cNodes = document.getElementsByClassName("employeeLi_" + id);
            for (var i = cNodes.length - 1; i >= 0; i--) {
                cNodes[i].style.color = "white";
                cNodes[i].style.backgroundColor = "#707070";
                cNodes[i].style.font = "14pt Segoe UI";
            }
        }


        selectedDepartment = node;
        liName.innerHTML = selectedDepartment.innerText.split("\n")[0];
        node.style.color = "white";
        node.style.backgroundColor = "#707070";
        var cNodes = document.getElementsByClassName("employeeLi_" + nodeId);
        for (var i = cNodes.length - 1; i >= 0; i--) {
            cNodes[i].style.color = "white";
        }

        if (selectedMonth != -1){
            document.getElementById("month_"+selectedMonth).selected = "selected";
            document.getElementById("year_"+selectedYear).selected = "selected";
        }

        document.getElementById("blockSelectDateEmp").style.display = "none";
        document.getElementById("blockSelectDateDep").style.display = "flex";

        if (maxDate != null){
            var minDate = document.getElementById("inputEmpMin").value.split("-");

            document.getElementById("selectMonth").selectedIndex = minDate[1]-1;
            document.getElementById("selectYear").value = minDate[0];

            maxDate = null;
            minDate = null;
        }


        selectedEmployee = false;
    }
    getRightPanelContent();
}

function selectEmployee( event ){
    var node = event.target;
    selectedEmployeeId = node.id.split("_")[1];

    var nodeId = selectedDepartment.id.split("_")[1];
    selectedDepartment.style.backgroundColor = "white";
    selectedDepartment.style.color = "#707070";
    var cNodes = document.getElementsByClassName("employeeLi_" + nodeId);
    for (var i = cNodes.length - 1; i >= 0; i--) {
        cNodes[i].style.color = "#707070";
        cNodes[i].style.backgroundColor = "white";
        cNodes[i].style.font = "14pt Segoe UI";
    }

    node.style.backgroundColor = "#707070";
    node.style.color = "white";
    node.style.font = "bold 14pt Segoe UI";


    document.getElementById("blockSelectDateDep").style.display = "none";
    document.getElementById("blockSelectDateEmp").style.display = "block";

    if (maxDate == null){
        if (selectedMonth < 9){
            document.getElementById("inputEmpMin").value = selectedYear + "-0" + (selectedMonth+1) + "-01";
            document.getElementById("inputEmpMax").value = selectedYear + "-0" + (selectedMonth+1) + "-" + daysInMoth;
        }else {
            document.getElementById("inputEmpMin").value = selectedYear + "-" + (selectedMonth+1) + "-01";
            document.getElementById("inputEmpMax").value = selectedYear + "-" + (selectedMonth+1) + "-" + daysInMoth;
        }
    }

    selectedEmployee = true;

    getRightPanelContent();
}

function overEmployee( event ){
    event.target.style.font = "bold 14pt Segoe UI";
}

function outEmployee( event ){
    event.target.style.font = "14pt Segoe UI";
}
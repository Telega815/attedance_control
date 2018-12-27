var selectedDepartment;
var selectedEmployee = false;

$(document).ready(function () {
    var nodes = document.getElementsByClassName("departmentLi")
    var ev = {};
    ev.target = nodes[0];
    selectDepartment(ev);
});

function getRightPanelContent(){
    var obj;

    var month = document.getElementById("selectMonth");
    var year = document.getElementById("selectYear");
    obj = {
        selectedEmployee: selectedEmployee,
        depId: selectedDepartment.id.split("_")[1],
        month: month.selectedIndex,
        year: year.value
    };
    var data = JSON.stringify(obj);

    var docURL = window.location.protocol +"//"+window.location.host + "/restService/getRightPanelContent?"+csrfParameter+"="+csrfToken;

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: docURL,
        method: 'POST',
        data: data,
        success: function(data){
            var rb = document.getElementById("rightBlock");
            rb.innerHTML = data;
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

        selectedEmployee = false;
    }
    getRightPanelContent();
}

function selectEmployee( event ){
    var node = event.target;

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

    selectedEmployee = true;
}

function overEmployee( event ){
    event.target.style.font = "bold 14pt Segoe UI";
}

function outEmployee( event ){
    event.target.style.font = "14pt Segoe UI";
}
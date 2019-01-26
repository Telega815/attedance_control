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
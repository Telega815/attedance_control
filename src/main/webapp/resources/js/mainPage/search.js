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

        li.id = "searchedDep_"+depRes[i].id.split("_")[1];
        li.classList.add("searchedLi");

        li.innerText = depRes[i].textContent;
        li.style.font = "bold 14pt Segoe UI";

        li.onclick = function (event) {
            selectedEmployee = false;
            selectedDepartment = event.target;
            var srchRes = document.getElementsByClassName("searchedLi");
            for (var j = 0; j < srchRes.length; j++){
                srchRes[j].style.backgroundColor = "white";
                srchRes[j].style.color = "#707070";
            }
            event.target.style.backgroundColor = "#707070";
            event.target.style.color = "white";
            getRightPanelContent();
        };

        searchList.appendChild(li);
    }


    for (var i = 0; i < empRes.length; i++){
        var li = document.createElement("li");

        li.innerText = empRes[i].textContent;
        li.style.font = "14pt Segoe UI";

        li.id = "searchedEmpl_"+empRes[i].id.split("_")[1];
        li.classList.add("searchedLi");

        li.onclick = function (event) {
            selectedEmployee = true;
            selectedEmployeeId = event.target.id.split("_")[1];
            var srchRes = document.getElementsByClassName("searchedLi");
            for (var j = 0; j < srchRes.length; j++){
                srchRes[j].style.backgroundColor = "white";
                srchRes[j].style.color = "#707070";
            }
            event.target.style.backgroundColor = "#707070";
            event.target.style.color = "white";
            getRightPanelContent();
        };
        searchList.appendChild(li);
    }


    depList.style.display = "none";
    searchList.style.display = "block";
}


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

        if (minYearT == 0 || maxYearT == 0){
            var rb = document.getElementById("rightBlock");
            rb.innerHTML = "Ошибка: Дата указана неверно!";
            return;
        }

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


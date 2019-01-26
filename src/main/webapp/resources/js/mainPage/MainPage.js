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
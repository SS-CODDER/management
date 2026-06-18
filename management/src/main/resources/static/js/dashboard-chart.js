fetch('/admin/dashboard-data')

.then(response => response.json())

.then(data => {

    createFeesChart(data.monthlyFees);

    createStudentChart(data.monthlyStudents);

    createAttendanceChart(
        data.presentCount,
        data.absentCount
    );
	loadRecentStudents(
	    data.recentStudents);

	loadRecentFees(
	    data.recentFees);

	loadUpcomingExams(
	    data.upcomingExams);

});

function createFeesChart(fees){

new Chart(
document.getElementById("feesChart"),
{
type:'bar',

data:{
labels:[
'Jan','Feb','Mar','Apr',
'May','Jun','Jul','Aug',
'Sep','Oct','Nov','Dec'
],

datasets:[{
label:'Fees Collection',
data:fees
}]
},

options:{
responsive:true,
maintainAspectRatio:false
}

});
}

function createStudentChart(students){

new Chart(
document.getElementById("studentChart"),
{
type:'line',

data:{
labels:[
'Jan','Feb','Mar','Apr',
'May','Jun','Jul','Aug',
'Sep','Oct','Nov','Dec'
],

datasets:[{
label:'Student Growth',
data:students,
fill:true
}]
},

options:{
responsive:true,
maintainAspectRatio:false
}

});
}

function createAttendanceChart(
present,
absent){

new Chart(
document.getElementById("attendanceChart"),
{
type:'doughnut',

data:{
labels:['Present','Absent'],

datasets:[{
data:[present,absent]
}]
},

options:{
responsive:true,
maintainAspectRatio:false
}

});
}


function loadRecentStudents(students){

let html='';

students.forEach(s=>{

html += `
<tr>
<td>${s.admissionNo}</td>
<td>${s.firstName}</td>
</tr>
`;

});

document
.getElementById("recentStudents")
.innerHTML = html;

}

function loadRecentFees(fees){

let html='';

fees.forEach(f=>{

html += `
<tr>
<td>${f.receiptNo}</td>
<td>${f.student.firstName}</td>
<td>${f.paidAmount}</td>
</tr>
`;

});

document
.getElementById("recentFees")
.innerHTML = html;

}

function loadUpcomingExams(exams){

let html='';

exams.forEach(e=>{

html += `
<li>
${e.examName}
(${e.subject})
</li>
`;

});

document
.getElementById("upcomingExams")
.innerHTML = html;

}
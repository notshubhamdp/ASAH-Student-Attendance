// reports.js
// Add interactivity for reports page here

document.addEventListener('DOMContentLoaded', function() {
    // Example: Highlight reports section
    const reportsSection = document.getElementById('reportsSection');
    if (reportsSection) {
        reportsSection.style.borderColor = '#38bdf8';
    }
    // Add more reports-specific JS logic here

    const defaulterForm = document.getElementById('defaulterForm');
    if (defaulterForm) {
        defaulterForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;
            const threshold = document.getElementById('percentageThreshold').value;
            fetch(`/api/reports/defaulters?startDate=${startDate}&endDate=${endDate}&percentageThreshold=${threshold}`)
                .then(response => response.json())
                .then(defaulters => {
                    const table = document.getElementById('defaulterTable');
                    const tbody = table.querySelector('tbody');
                    tbody.innerHTML = '';
                    if (defaulters.length > 0) {
                        table.style.display = '';
                        document.getElementById('noDefaulters').style.display = 'none';
                        defaulters.forEach(d => {
                            const row = document.createElement('tr');
                            row.innerHTML = `<td>${d.rollNumber || ''}</td><td>${d.firstName} ${d.lastName}</td><td>${d.email}</td><td>${d.branch || ''}</td><td>${d.attendancePercentage.toFixed(2)} %</td>`;
                            tbody.appendChild(row);
                        });
                    } else {
                        table.style.display = 'none';
                        document.getElementById('noDefaulters').style.display = '';
                    }
                })
                .catch(() => {
                    document.getElementById('defaulterTable').style.display = 'none';
                    document.getElementById('noDefaulters').style.display = '';
                });
        });
    }
    const exportPdfBtn = document.getElementById('exportPdfBtn');
    const exportExcelBtn = document.getElementById('exportExcelBtn');
    function getExportParams() {
        return {
            startDate: document.getElementById('startDate').value,
            endDate: document.getElementById('endDate').value,
            threshold: document.getElementById('percentageThreshold').value
        };
    }
    if (exportPdfBtn) {
        exportPdfBtn.addEventListener('click', function() {
            const params = getExportParams();
            const url = `/api/reports/defaulters/export/pdf?startDate=${params.startDate}&endDate=${params.endDate}&percentageThreshold=${params.threshold}`;
            window.open(url, '_blank');
        });
    }
    if (exportExcelBtn) {
        exportExcelBtn.addEventListener('click', function() {
            const params = getExportParams();
            const url = `/api/reports/defaulters/export/excel?startDate=${params.startDate}&endDate=${params.endDate}&percentageThreshold=${params.threshold}`;
            window.open(url, '_blank');
        });
    }
    const trendForm = document.getElementById('trendForm');
    if (trendForm) {
        trendForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const startDate = document.getElementById('trendStartDate').value;
            const endDate = document.getElementById('trendEndDate').value;
            fetch(`/api/reports/attendance-trend?startDate=${startDate}&endDate=${endDate}`)
                .then(response => response.json())
                .then(trend => {
                    const labels = trend.map(d => d.date);
                    const data = trend.map(d => d.attendancePercentage);
                    const ctx = document.getElementById('attendanceTrendChart').getContext('2d');
                    if (window.attendanceTrendChart) {
                        window.attendanceTrendChart.destroy();
                    }
                    window.attendanceTrendChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Attendance %',
                                data: data,
                                backgroundColor: 'rgba(56,189,248,0.2)',
                                borderColor: '#38bdf8',
                                borderWidth: 2,
                                fill: true
                            }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                legend: { position: 'top' },
                                tooltip: { mode: 'index', intersect: false }
                            },
                            interaction: { mode: 'index', intersect: false },
                            scales: {
                                x: { title: { display: true, text: 'Date' } },
                                y: { title: { display: true, text: 'Attendance (%)' }, beginAtZero: true, max: 100 }
                            }
                        }
                    });
                });
        });
    }
});

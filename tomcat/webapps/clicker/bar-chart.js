var ctx = document.getElementById('myChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'bar',

    // The data for our dataset
    data: {
        labels: ['A', 'B', 'C', 'D'],
        datasets: [{
        label: 'Question 8',
        backgroundColor: 'rgb(255, 99, 132)',
        borderColor: 'rgb(255, 99, 132)',
        data: [4, 10, 5, 2]
        }],
        borderWidth: 1
    },

    // Configuration options go here
    options: {
        legend:{display: false,},label: {display: false,},
        scales: {
            scaleLabel: {
                fontColor: '#424242',
            },
            yAxes: [{
                ticks: {
                    beginAtZero: true
                },
                gridLines: {display: false,},
            }],
            xAxes: [{gridLines: {display: false,}}],
        }
    }
});
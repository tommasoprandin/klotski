const Klotski = require('klotski');

const solver = new Klotski();

// const game = {
//     blocks: [
//         { "shape": [2, 2], "position": [0, 1] },
//         { "shape": [2, 1], "position": [0, 0] },
//         { "shape": [2, 1], "position": [0, 3] },
//         { "shape": [2, 1], "position": [2, 0] },
//         { "shape": [2, 1], "position": [2, 3] },
//         { "shape": [1, 2], "position": [2, 1] },
//         { "shape": [1, 1], "position": [3, 1] },
//         { "shape": [1, 1], "position": [3, 2] },
//         { "shape": [1, 1], "position": [4, 0] },
//         { "shape": [1, 1], "position": [4, 3] },
//     ],
//     boardSize: [5, 4],
//     escapePoint: [3, 1],
// };

exports.handler = async (event) => {
    const game = JSON.parse(event.body);
    console.log(game);
    const nextMove = solver.solve(game).at(0);
    return {
        statusCode: 200,
        body: JSON.stringify(nextMove),
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Credentials": true,
        },
    };
};
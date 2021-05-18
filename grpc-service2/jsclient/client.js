const grpc = require("grpc")
const protoLoader = require("@grpc/proto-loader")
const packageDef = protoLoader.loadSync("calculator.proto", {})
const grpcObject = grpc.loadPackageDefinition(packageDef)
const calculatorPackage = grpcObject.calculator

const client = new calculatorPackage.CalculatorService("localhost:50051", grpc.credentials.createInsecure())

client.Sum({
    "firstNumber": 12,
    "secondNumber": 25
}, (err, res) => {
    console.log("Response: " + res.result)
})
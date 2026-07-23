const resposta = await login(username, password);

console.log("Resposta da API:", resposta);
console.log("Token:", resposta.token);

localStorage.setItem("token", resposta.token);

alert("Login realizado");

navigate("/dashboard");
import api from "./api";

export async function login(username, password) {
  const response = await api.post("/auth/login", null, {
    params: {
      username,
      password,
    },
  });

  return response.data;
}

export async function register(username, password) {
  const response = await api.post("/auth/register", null, {
    params: {
      username,
      password,
    },
  });

  return response.data;
}
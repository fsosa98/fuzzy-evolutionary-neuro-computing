import random
import math
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D
from mpl_toolkits import mplot3d
import matplotlib.pyplot as plt


class Anfis:

    def __init__(self, n, eta1, eta2, num_iter):
        self.n = n
        self.eta1 = eta1
        self.eta2 = eta2
        self.num_iter = num_iter
        self.a = self.initialize(n)
        self.b = self.initialize(n)
        self.c = self.initialize(n)
        self.d = self.initialize(n)
        self.p = self.initialize(n)
        self.q = self.initialize(n)
        self.r = self.initialize(n)
        self.train_set = self.initialize_train_set()



    def initialize(self, n):
        x = []
        for i in range(n):
            x.append(2 * random.random() - 1)
        return x

    def zero_initialization(self, n):
        x = []
        for i in range(n):
            x.append(0)
        return x

    def initialize_train_set(self):
        l = []
        for x_i in range(-4, 5):
            for y_i in range(-4, 5):
                l_i = []
                l_i.append(x_i)
                l_i.append(y_i)
                l_i.append(self.f(x_i, y_i))
                l.append(l_i)
        return l

    def sigmoid(self, x, a, b):
        if (b * (x - a)) >= 100:
            return 1.
        if (b * (x - a)) <= -100:
            return -1.
        return 1. / (1 + math.exp(b * (x - a)))

    def f(self, x, y):
        return ((x - 1) ** 2 + (y + 2) ** 2 - 5 * x * y + 3) * math.cos(x / 5) ** 2

    def update_parameters(self):
        for i in range(self.n):
            self.p[i] += self.eta1 * self.dp[i]
            self.q[i] += self.eta1 * self.dq[i]
            self.r[i] += self.eta1 * self.dr[i]
            self.a[i] += self.eta2 * self.da[i]
            self.b[i] += self.eta2 * self.db[i]
            self.c[i] += self.eta2 * self.dc[i]
            self.d[i] += self.eta2 * self.dd[i]

    def set_all_to_zero(self):
        self.da = self.zero_initialization(self.n)
        self.db = self.zero_initialization(self.n)
        self.dc = self.zero_initialization(self.n)
        self.dd = self.zero_initialization(self.n)
        self.dp = self.zero_initialization(self.n)
        self.dq = self.zero_initialization(self.n)
        self.dr = self.zero_initialization(self.n)

    def train(self, batch=True):
        self.E_all = []
        for iter in range(self.num_iter):

            self.set_all_to_zero()

            E = 0
            for sample in self.train_set:
                x, y, f = sample[0], sample[1], sample[2]

                z = []
                pi = []
                alpha = []
                beta = []
                pi_sum = 0
                for i in range(self.n):
                    alpha.append(self.sigmoid(x, self.a[i], self.b[i]))
                    beta.append(self.sigmoid(y, self.c[i], self.d[i]))
                    pi.append(alpha[i] * beta[i])
                    pi_sum += pi[i]
                    z.append(self.p[i] * x + self.q[i] * y + self.r[i])

                #Output
                o = 0
                for i in range(self.n):
                    o += pi[i] * z[i]
                o /= pi_sum

                err = f - o
                E += (err ** 2)
                for i in range(self.n):
                    self.dp[i] += err * (pi[i] / pi_sum) * x
                    self.dq[i] += err * (pi[i] / pi_sum) * y
                    self.dr[i] += err * (pi[i] / pi_sum)

                for i in range(self.n):
                    numerator = 0
                    for j in range(self.n):
                        if i != j:
                            numerator += pi[j] * (z[i] - z[j])
                    koef = numerator / (pi_sum ** 2)

                    self.da[i] += err * koef * beta[i] * self.b[i] * alpha[i] * (1 - alpha[i])
                    self.db[i] -= err * koef * beta[i] * (x - self.a[i]) * alpha[i] * (1 - alpha[i])
                    self.dc[i] += err * koef * alpha[i] * self.d[i] * beta[i] * (1 - beta[i])
                    self.dd[i] -= err * koef * alpha[i] * (y - self.c[i]) * beta[i] * (1 - beta[i])

                if not batch:
                    self.update_parameters()
                    self.set_all_to_zero()

            self.E_all.append(E)
            if batch:
                self.update_parameters()
                self.set_all_to_zero()

            if iter % (int(self.num_iter / 50)) == 0:
                # print("p: " + str(self.p))
                # print("q: " + str(self.q))
                # print("r: " + str(self.r))
                # print("a: " + str(self.a))
                # print("a: " + str(self.a))
                print("E(iter =", iter, ") =", E / len(self.train_set))

    def plot_3(self):
        all = np.array(self.train_set)
        # print(all)
        X = np.array(all[:, 0])
        Y = np.array(all[:, 1])
        Z = np.array(all[:, 2])

        fig = plt.figure(figsize=(9, 10))
        ax = plt.axes(projection='3d')
        ax.plot_surface(np.array(np.split(X, 9)), np.split(Y, 9), np.array(np.split(Z, 9)), cmap=cm.coolwarm,
                        linewidth=0, antialiased=False)
        plt.show()

    def plot(self):
        o = []
        all = np.array(self.train_set)
        X = np.array(all[:, 0])
        Y = np.array(all[:, 1])
        z_real = np.array(all[:, 2])
        err = []
        j = 0
        for sample in self.train_set:
            x, y, f = sample[0], sample[1], sample[2]

            z = []
            pi = []
            alpha = []
            beta = []
            pi_sum = 0
            for i in range(self.n):
                alpha.append(self.sigmoid(x, self.a[i], self.b[i]))
                beta.append(self.sigmoid(y, self.c[i], self.d[i]))
                pi.append(alpha[i] * beta[i])
                pi_sum += pi[i]
                z.append(self.p[i] * x + self.q[i] * y + self.r[i])

            # Izlaz
            o_i = 0
            for i in range(self.n):
                o_i += pi[i] * z[i]
            o_i /= pi_sum
            o.append(o_i)
            err.append(o_i - z_real[j])
            j += 1

        Z = np.array(o)
        fig = plt.figure(figsize=(9, 10))
        ax = plt.axes(projection='3d')
        ax.plot_surface(np.array(np.split(X, 9)), np.split(Y, 9), np.array(np.split(Z, 9)), cmap=cm.coolwarm,
                        linewidth=0, antialiased=False)
        ax.set_title('Funkcija')
        plt.show()

        Err = np.array(err)
        fig = plt.figure(figsize=(9, 10))
        ax = plt.axes(projection='3d')
        ax.plot_surface(np.array(np.split(X, 9)), np.split(Y, 9), np.array(np.split(Err, 9)), cmap=cm.coolwarm,
                        linewidth=0, antialiased=False)
        ax.set_title('Greška')
        plt.show()

    def plot_5(self):
        X = np.arange(start=-20, stop=20, step=0.1)

        for i in range(self.n):
            alpha = []
            beta = []
            j= 0
            for x in X:
                alpha.append(self.sigmoid(x, self.a[i], self.b[i]))
                beta.append(self.sigmoid(x, self.c[i], self.d[i]))
            alpha = np.array(alpha)
            beta = np.array(beta)
            plt.subplot(self.n, 2, i * 2 + 1)
            plt.plot(X, alpha)
            plt.subplot(self.n, 2, i * 2 + 2)
            plt.plot(X, beta)
        plt.show()

    def plot_7(self, E1, E2):
        X = np.arange(start=-0, stop=self.num_iter, step=1)
        plt.plot(X, E1, label = 'Stohastic')
        plt.plot(X, E2, label = 'Batch')
        plt.legend()
        plt.show()

    def plot_8(self, E1, E2, E3):
        X = np.arange(start=-0, stop=self.num_iter, step=1)
        plt.plot(X, E1, label='eta1=0.001, eta2=0.000001')
        plt.plot(X, E2, label='eta1=0.01, eta2=0.000012')
        plt.plot(X, E3, label='eta1=0.4, eta2=0.0001')
        plt.legend()
        plt.show()
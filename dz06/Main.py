from Anfis import *
import numpy as np


if __name__ == "__main__":
    n = 5
    anfis = Anfis(n=n, eta1=0.01, eta2=0.000012, num_iter=1000)
    anfis.train(False)
    anfis.plot_5()
    # E1 = anfis.E_all
    # anfis2 = Anfis(n=n, eta1=0.01, eta2=0.000012, num_iter=1000)
    # anfis2.train(False)
    # E2 = anfis2.E_all
    # anfis3 = Anfis(n=n, eta1=0.4, eta2=0.0001, num_iter=1000)
    # anfis3.train(False)
    # E3 = anfis3.E_all
    # anfis.plot_8(E1, E2, E3)
    #anfis.plot_f()
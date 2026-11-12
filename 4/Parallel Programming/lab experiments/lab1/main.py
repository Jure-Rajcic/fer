from mpi4py import MPI
import random
import time
import sys

# mpiexec -n 3 python3 main.py

class Philosopher:
    def __init__(self, rank):
        self.rank = rank
        # Fork possession (0:no fork, 1:dirty, 2:clean)
        self.lf = 0 #left fork
        self.rf = 0 #right fork

        # Philosopher's neighbor rank
        self.ln = -1 # left neighbor
        self.rn = -1 # right neighbor

        # Request memo (0:no request, 1:pending request)
        self.lr = 0
        self.rr = 0

    def __repr__(self):
        return "\n" + "left neighbor:" + str(self.ln) + " | fork left:" + str(self.lf) + " <- [rank:" + str(self.rank) + "] -> fork right:" + str(self.rf) + " | right neighbor:" + str(self.rn)

    def output(self, message):
        print(self.rank * "\t" + message + " - forks=[" + str(self.lf) + ", " + str(self.rf) + "]")
        sys.stdout.flush()


# MPI initialization
comm = MPI.COMM_WORLD
p = Philosopher(comm.Get_rank())
size = comm.size

if size < 2:
    print("❌ less than two philosophers!")
    exit(1)

if p.rank == 0:
    p.ln = size - 1
    p.rn = 1
elif p.rank == size - 1:
    p.ln = p.rank - 1
    p.rn = 0
else:
    p.ln = p.rank - 1
    p.rn = p.rank + 1

if p.rank == 0:
    p.lf = 1
    p.rf = 1
elif p.rank < comm.size - 1:
    p.rf = 1


def think(sec):
    p.output("Thinking for " + str(sec) + " seconds")
    while sec > 0:  

        if comm.iprobe(source=p.ln, tag=1):
            recv = comm.recv(source=p.ln, tag=1)
            if recv == 2: # requesting left fork
                p.lr = 1

        if p.lr:
            p.output("Others are looking for my left fork.")
            if p.lf != 0:  
                p.output("I'm sending my left fork!")
                p.lf = 2  
                comm.send(p.lf, dest=p.ln, tag=4)
                p.lr = 0  
                p.lf = 0  


        if comm.iprobe(source=p.rn, tag=1):
            recv = comm.recv(source=p.rn, tag=1)
            if recv == 1: # requested right fork
                p.rr = 1

        if p.rr:
            p.output("Others are looking for my right fork.")
            if p.rf != 0:  
                p.output("I'm sending my right fork!")
                p.rf = 2  
                comm.send(p.rf, dest=p.rn, tag=4)
                p.rr = 0  
                p.rf = 0  
        time.sleep(1) 
        sec -= 1


def get_fork():
    while p.lf == 0 or p.rf == 0:  
        left = False  
        right = False  
        if p.lf == 0:
            left = True
            data = 1  
            src = p.ln
            p.output("Looking for left fork")
        elif p.rf == 0:
            right = True
            data = 2  
            src = p.rn
            p.output("Looking for right fork")
        comm.send(data, dest=src, tag=1)
        while left or right:  
            st = MPI.Status()  
            if left:
                p.output("Waiting for a response for left fork...")
            elif right:
                p.output("Waiting for a response for right fork...")

            data = comm.recv(source=MPI.ANY_SOURCE, tag=MPI.ANY_TAG, status=st)
            if st.Get_tag() == 4:  
                
                if st.Get_source() == p.ln and p.lf == 0 and left:
                    p.lf = data
                    p.output("I have received my left fork.")
                    left = False
                    
                if st.Get_source() == p.rn and p.rf == 0 and right:
                    p.rf = data
                    p.output("I have received my right fork.")
                    right = False
                    
            elif st.Get_tag() == 1:  
                if st.Get_source() == p.ln and data == 2 or p.lr == 1:
                    p.output("Others are looking for my left fork.")
                    if p.lf == 1:
                        p.output("I'm sending my left fork!")
                        p.lf = 2
                        comm.send(p.lf, dest=p.ln, tag=4)
                        p.lf = 0
                    elif p.lf == 2:
                        p.output("My left fork is clean, I won't send it yet.")
                        p.lr = 1

                        
                elif st.Get_source() == p.rn and data == 1 or p.rr == 1:
                    p.output("Others are looking for my right fork.")
                    if p.rf == 1:
                        p.output("I'm sending my right fork.")
                        p.rf = 2
                        comm.send(p.rf, dest=p.rn, tag=4)
                        p.rf = 0
                    elif p.rf == 2:
                        p.output("My right fork is clean, I won't send it yet.")
                        p.rr = 1



def eat(num_of_seconds):
    p.output("I'm eating for " + str(num_of_seconds) + " seconds...")
    time.sleep(num_of_seconds)
    p.lf = 1
    p.rf = 1
    p.output("I have finished eating.")
    if comm.iprobe(source=p.ln, tag=1):
        received_data = comm.recv(source=p.ln, tag=1)
        if received_data == 2:
            p.lr = 1
        else:
            p.rr = 1
    if comm.iprobe(source=p.rn, tag=1):
        received_data = comm.recv(source=p.rn, tag=1)
        if received_data == 1:
            p.rr = 1
        else:
            p.lr = 1

    if p.lr:
        p.output("There is a pending request for my left fork, I'm sending it!")
        p.lf = 2
        comm.send(p.lf, dest=p.ln, tag=4)
        p.lf = 0
        p.lr = 0
    if p.rr:
        p.output("There is a pending request for my right fork, I'm sending it!")
        p.rf = 2
        comm.send(p.rf, dest=p.rn, tag=4)
        p.rf = 0
        p.rr = 0


while True:
    think(random.randint(5, 10))
    get_fork()
    eat(random.randint(5, 10))

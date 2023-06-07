#include <iostream>
#include <vector>
#include <set>
#include <string>

// Generic Programming (C++)
template <typename Iterator, typename Predicate> Iterator mymax(Iterator first, Iterator last, Predicate pred)
{
    Iterator max_element = first;
    for (Iterator it = first; it != last; ++it)
    {
        if (pred(*it, *max_element))
        {
            max_element = it;
        }
    }
    return max_element;
}

int gt_int(int a, int b) { return a > b; }
bool gt_str(const std::string &a, const std::string &b) { return a > b; }

//  g++ -std=c++11 2.cpp -o 2 && ./2
int main()
{
    int arr_int[] = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    std::vector<int> vec_int = {1, 3, 5, 7, 4, 6, 9, 2, 0};
    std::set<int> set_int = {1, 3, 5, 7, 4, 6, 9, 2, 0};

    auto max_arr_int = mymax(std::begin(arr_int), std::end(arr_int), gt_int);
    auto max_vec_int = mymax(vec_int.begin(), vec_int.end(), gt_int);
    auto max_set_int = mymax(set_int.begin(), set_int.end(), gt_int);

    std::cout << "Max int (array): " << *max_arr_int << std::endl;
    std::cout << "Max int (vector): " << *max_vec_int << std::endl;
    std::cout << "Max int (set): " << *max_set_int << std::endl;

    const std::string arr_str[] = {"Gle", "malu", "vocku", "poslije", "kise"};
    std::vector<std::string> vec_str = {"Gle", "malu", "vocku", "poslije", "kise"};
    std::set<std::string> set_str = {"Gle", "malu", "vocku", "poslije", "kise"};

    auto max_arr_str = mymax(std::begin(arr_str), std::end(arr_str), gt_str);
    auto max_vec_str = mymax(vec_str.begin(), vec_str.end(), gt_str);
    auto max_set_str = mymax(set_str.begin(), set_str.end(), gt_str);

    std::cout << "Max str (array): " << *max_arr_str << std::endl;
    std::cout << "Max str (vector): " << *max_vec_str << std::endl;
    std::cout << "Max str (set): " << *max_set_str << std::endl;

    return 0;
}

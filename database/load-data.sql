SELECT list_random_current_registration()
FROM GENERATE_SERIES(1, 100);
SELECT list_random_prefix_registration()
FROM GENERATE_SERIES(1, 100);
SELECT list_random_suffix_registration()
FROM GENERATE_SERIES(1, 100);